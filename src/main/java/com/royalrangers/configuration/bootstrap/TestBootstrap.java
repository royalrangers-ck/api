package com.royalrangers.configuration.bootstrap;

import com.royalrangers.dto.achievement.TestRequestDto;
import com.royalrangers.model.achievement.Test;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestBootstrap {

    public List<TestRequestDto> createTest(){
        List<TestRequestDto> tests = new ArrayList<>();
        for (int element = 1; element< 6; element++){
            Test test = new Test();
            TestRequestDto testRequestDto = new TestRequestDto();
            switch (element) {
                case 1: {
                    testRequestDto.setName("Дика природа");
                    testRequestDto.setShortDescription("Синій тест майстерності. Ти дізнаєшся багато нового про дику природу та зможеш\n"
                            +"здійснити невеликий похід зі своєю ланкою.");
                    testRequestDto.setDescription(
                        "Ти дізнаєшся що таке охорона природи.\n" +
                        "Ти дізнаєшся наскільки важливою була дика природа для виживання людини протягом усієї історії.\n" +
                        "Ти дізнаєшся чому так важливо зберігати природу.\n" +
                        "Ти навчишся розпізнавати сліди тварин для того, щоб дізнатись про них більше.\n" +
                        "Ти навчишся виготовлятии гіпсовий зліпок сліду, знайденого на природі.\n" +
                        "Ти дізнаєшся про різні місця мешкання, а також описати те, як диким тваринам може\n" +
                        "загрожувати вимирання, якщо не виконуватимуться закони про Охорону.\n" +
                        "Ти дізнаєшся про дві проблеми, які впливають на дику природу краю (області, країни).\n" +
                        "Ти дізнаєшся, чому охорона й збереження природи є корисними для нас самих.\n" +
                        "Ти дізнаєшся про способи охорони природи."
                    );
                    testRequestDto.setLogoUrl(null);
                    testRequestDto.setTestType(1);
                    testRequestDto.setQuarterAchievementId(1);
                }
                case 2: {
                    testRequestDto.setName("Догляд заСобакою");
                    testRequestDto.setShortDescription("Ти дізнаєшся багато нового про собаку та догляд про неї.");
                    testRequestDto.setDescription(
                        "Ти зможеш назвати частини тіла собаки й підписати на малюнку.\n" +
                        "Ти зможеш обговорити користь собак для людей.\n" +
                        "Ти зможеш розпізнати деякі з основних відмінностей між деякими групами собак.\n" +
                        "Ти зможеш розповісти, за якими характерними ознаками оцінюють собак на великих змаганнях.\n" +
                        "Ти зможеш назвати деякі породи собак, а також деякі характеристики, які кожну з цих порід роблять унікальною.\n" +
                        "Ти зможеш назвати деякі породи собак, а також деякі характеристики, які кожну з цих порід роблять унікальною.\n" +
                        "Ти зможеш пояснити, що значить доглядати за собакою: годувати, забезпечувати водою, грумінг, фізичні вправи й купання.\n" +
                        "Ти зможеш розповісти про виховання та дресирування собаки."
                    );
                    testRequestDto.setLogoUrl(null);
                    testRequestDto.setTestType(1);
                    testRequestDto.setQuarterAchievementId(1);
                }
                case 3: {
                    testRequestDto.setName("Лешинг");
                    testRequestDto.setShortDescription("Червоний тест майстерності. Для його виконання, тобі потрібно закінчити тест Робота з " +
                            "Мотузкою. Ти навчишся робити багато корисних в поході речей за допомогою лешингу.");
                    testRequestDto.setDescription(
                            "Ти вивчиш квадратний, діагональний, круговий, паралельний та безперервний лешинг. Ти навчишся робити туристичні меблі.\n" +
                            "Діти зможуть правильно виконати квадратне кріплення.");
                    testRequestDto.setLogoUrl(null);
                    testRequestDto.setTestType(5);
                }
                case 4: {
                    testRequestDto.setName("Приготування їжї");
                    testRequestDto.setShortDescription("Червоний тест майстерності. Цей Тест Майстерності є обов’язковим для отримання Золотої Медалі Досягнень. Ти навчишся складати меню для походу та готувати їжу у фользі.");
                    testRequestDto.setDescription(
                        "Діти дізнаються про харчову піраміду. 2. Діти дізнаються про розмір рекомендованої порції на одну людину. 3. Діти почнуть планувати меню для походу на 6 дітей. знаються про харчову піраміду. Діти дізнаються про харчову піраміду. 1. Діти дізнаються про харчову піраміду. 2. Діти дізнаються про розмір рекомендованої порції на одну людину. 3. Діти почнуть планувати меню для походу на 6 дітей. 1. Діти дізнаються про харчову піраміду. 2. Діти дізнаються про розмір рекомендованої порції на одну людину. 3. Діти почнуть планувати меню для походу на 6 дітей. Діти почнуть планувати меню для походу на 6 дітей. Ти дізнаєшся про харчові піраміду\n" +
                        "Ти дізнаєшся про розмір рекомендованої порції на одну людину\n" +
                        "Ти навчишся планувати меню для походу\n" +
                        "Ти навчишся розраховувати вартість меню для походу\n" +
                        "Ти навчишся обладнувати місце для миття посуду способом «мий, промивай, промивай»\n" +
                        "Діти навчаться обладнувати місце для миття посуду способом «помий і двічі сполосни». Ти навчишся обладнувати місце для миття рук\n" +
                        "Ти навчишся випікати кекси на багатті\n" +
                        "Ти навчишся готувати в фользі");
                    testRequestDto.setLogoUrl(null);
                    testRequestDto.setTestType(5);
                    testRequestDto.setQuarterAchievementId(1);
                }
                case 5: {
                    testRequestDto.setName("Розведення багаття");
                    testRequestDto.setShortDescription("Червоний тест майстерності. Ти навчишся розпалювати багаття 6 видів.");
                    testRequestDto.setDescription(
                            "Ти зможеш назвати правила техніки безпеки при розпалюванні багаття.\n" +
                                    "Ти знатимеш про 4 види трута, хмиз та дрова.\n" +
                                    "Ти зможеш показати, як складається багаття Тіпі.\n" +
                                    "Ти зможут зібрати протипожежне обладнання для походу.\n" +
                                    "Ти зможеш пояснити, як боротися з вогнем у різних умовах: вдома, в лісі, на відкритому просторі.\n" +
                                    "Ти зможеш зібрати табірне протипожежне обладнання й пояснити його призначення.\n" +
                                    "Ти зможеш скласти багаття в вигляді літери А.\n" +
                                    "Ти зможеш назвати правила безпеки при поводженні з газовою плиткою.\n" +
                                    "Ти зможеш показати, як складається мисливське багаття та багаття ради.\n" +
                                    "Ти зможеш показати, як складається канавне багаття.\n" +
                                    "Ти навчишся виконувати всі правила безпеки.\n" +
                                    "Ти зможеш показати, як правильно гасити багаття.");
                    testRequestDto.setLogoUrl(null);
                    testRequestDto.setTestType(5);
                    testRequestDto.setQuarterAchievementId(1);
                }
            }
            tests.add(testRequestDto);
        };
        return tests;
    }
}
