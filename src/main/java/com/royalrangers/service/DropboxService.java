package com.royalrangers.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.royalrangers.enums.ImageType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;

@Service
public class DropboxService {

    @Value("${dropbox.appName}")
    private String appName;

    @Value("${dropbox.accessToken}")
    private String accessToken;

    @Value("${dropbox.avatarPrefix}")
    private String avatarPrefix;

    @Value("${dropbox.platoonLogoPrefix}")
    private String platoonLogoPrefix;

    @Value("${dropbox.achievementTestLogoPrefix}")
    private String achievementTestLogoPrefix;

    @Value("${dropbox.achievementQuarterLogoPrefix}")
    private String achievementQuarterLogoPrefix;

    @Value("${dropbox.achievementYearLogoPrefix}")
    private String achievementYearLogoPrefix;

    @Value("${dropbox.achievementThreeYearLogoPrefix}")
    private String achievementThreeYearLogoPrefix;

    @Value("${dropbox.achievementTwelveYearLogoPrefix}")
    private String achievementTwelveYearLogoPrefix;

    @Value("${dropbox.achievementRewardLogoPrefix}")
    private String achievementRewardLogoPrefix;

    private String getImagePrefix(ImageType imageType){
        String prefix = "";
        switch (imageType){
            case USER_AVATAR: {
                prefix = avatarPrefix;
                break;
            }
            case PLATOON_LOGO: {
                prefix = platoonLogoPrefix;
                break;
            }
            case TEST_LOGO: {
                prefix = achievementTestLogoPrefix;
                break;
            }
            case QUARTER_ACHIEVEMENT_LOGO: {
                prefix = achievementQuarterLogoPrefix;
                break;
            }
            case YEAR_ACHIEVEMENT_LOGO: {
                prefix = achievementYearLogoPrefix;
                break;
            }
            case THREE_YEAR_ACHIEVEMENT_LOGO: {
                prefix = achievementThreeYearLogoPrefix;
                break;
            }
            case TWELVE_YEAR_ACHIEVEMENT_LOGO: {
                prefix = achievementTwelveYearLogoPrefix;
                break;
            }
            case REWARD_LOGO: {
                prefix = achievementRewardLogoPrefix;
                break;
            }
        }
        return prefix;
    };

    private DbxClientV2 getClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(appName)
                .withUserLocale(Locale.getDefault().toString())
                .build();

        return new DbxClientV2(config, accessToken);
    }

    public String imageUpload(MultipartFile file, ImageType imageType) throws IOException, DbxException {

        String extension = getFilenameExtension(file.getOriginalFilename());
        String path = getImagePrefix(imageType) + UUID.randomUUID().toString() + extension;

        InputStream in = file.getInputStream();
        getClient().files().uploadBuilder(path).uploadAndFinish(in);

        return getSharedLink(path);
    }

    public void deleteImage(String avatarUrl, ImageType imageType) throws DbxException {
        String filename = avatarUrl.substring(avatarUrl.lastIndexOf("/") + 1);
        String path = getImagePrefix(imageType) + filename;
        getClient().files().delete(path);
    }

    private String getFilenameExtension (String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

    private String getSharedLink(String path) throws DbxException {
        String sharedUrl = getClient().sharing().createSharedLinkWithSettings(path).getUrl();
        String directUrl = sharedUrl
                .substring(0, sharedUrl.lastIndexOf("?"))
                .replaceAll("www.dropbox.com", "dl.dropboxusercontent.com");

        return directUrl;
    }

    public static Boolean isFileSizeCorrect(MultipartFile file, long maxFileSize) {
        if (file.getSize() <= maxFileSize)
            return true;
        else
            return false;
    }
 }
