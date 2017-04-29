package com.royalrangers.configuration.bootstrap;

import com.royalrangers.dto.achievement.TestRequestDto;
import com.royalrangers.model.achievement.Test;

import java.util.ArrayList;
import java.util.List;

public class TestBootstrap {

    public List<TestRequestDto> createTest() {
        List<TestRequestDto> tests = new ArrayList<>();
        for (int element = 1; element < 6; element++) {
            Test test = new Test();
            TestRequestDto testRequestDto = new TestRequestDto();
            switch (element) {
                case 1: {
                    testRequestDto.setName("Дика природа");
                    testRequestDto.setShortDescription("Синій тест майстерності. Ти дізнаєшся багато нового про дику природу та зможеш\n"
                            + "здійснити невеликий похід зі своєю ланкою.");
                    testRequestDto.setDescription(
                            "Ти дізнаєшся що таке охорона природи." +
                                    "Ти дізнаєшся наскільки важливою була дика природа для виживання людини протягом усієї історії." +
                                    "Ти дізнаєшся чому так важливо зберігати природу." +
                                    "Ти навчишся розпізнавати сліди тварин для того, щоб дізнатись про них більше." +
                                    "Ти навчишся виготовлятии гіпсовий зліпок сліду, знайденого на природі." +
                                    "Ти дізнаєшся про різні місця мешкання, а також описати те, як диким тваринам може" +
                                    "загрожувати вимирання, якщо не виконуватимуться закони про Охорону." +
                                    "Ти дізнаєшся про дві проблеми, які впливають на дику природу краю (області, країни)." +
                                    "Ти дізнаєшся, чому охорона й збереження природи є корисними для нас самих." +
                                    "Ти дізнаєшся про способи охорони природи."
                    );
                    testRequestDto.setLogoUrl(null);
                    testRequestDto.setTestType("BLUE");
                    testRequestDto.setQuarterAchievementId(1);
                    break;
                }
                case 2: {
                    testRequestDto.setName("Догляд заСобакою");
                    testRequestDto.setShortDescription("Ти дізнаєшся багато нового про собаку та догляд про неї.");
                    testRequestDto.setDescription(
                            "Ти зможеш назвати частини тіла собаки й підписати на малюнку." +
                                    "Ти зможеш обговорити користь собак для людей." +
                                    "Ти зможеш розпізнати деякі з основних відмінностей між деякими групами собак." +
                                    "Ти зможеш розповісти, за якими характерними ознаками оцінюють собак на великих змаганнях." +
                                    "Ти зможеш назвати деякі породи собак, а також деякі характеристики, які кожну з цих порід роблять унікальною." +
                                    "Ти зможеш назвати деякі породи собак, а також деякі характеристики, які кожну з цих порід роблять унікальною." +
                                    "Ти зможеш пояснити, що значить доглядати за собакою: годувати, забезпечувати водою, грумінг, фізичні вправи й купання." +
                                    "Ти зможеш розповісти про виховання та дресирування собаки."
                    );
                    testRequestDto.setLogoUrl(null);
                    testRequestDto.setTestType("BLUE");
                    testRequestDto.setQuarterAchievementId(1);
                    break;
                }
                case 3: {
                    testRequestDto.setName("Лешинг");
                    testRequestDto.setShortDescription("Червоний тест майстерності. Для його виконання, тобі потрібно закінчити тест Робота з " +
                            "Мотузкою. Ти навчишся робити багато корисних в поході речей за допомогою лешингу.");
                    testRequestDto.setDescription(
                            "Ти вивчиш квадратний, діагональний, круговий, паралельний та безперервний лешинг. Ти навчишся робити туристичні меблі." +
                                    "Діти зможуть правильно виконати квадратне кріплення.");
                    testRequestDto.setLogoUrl(null);
                    testRequestDto.setTestType("RED");
                    testRequestDto.setQuarterAchievementId(1);
                    break;
                }
                case 4: {
                    testRequestDto.setName("Приготування їжї");
                    testRequestDto.setShortDescription("Червоний тест майстерності. Цей Тест Майстерності є обов’язковим для отримання Золотої Медалі Досягнень. Ти навчишся складати меню для походу та готувати їжу у фользі.");
                    testRequestDto.setDescription(
                            "Діти дізнаються про харчову піраміду. 2. Діти дізнаються про розмір рекомендованої порції на одну людину. 3. Діти почнуть планувати меню для походу на 6 дітей. знаються про харчову піраміду. Діти дізнаються про харчову піраміду. 1. Діти дізнаються про харчову піраміду. 2. Діти дізнаються про розмір рекомендованої порції на одну людину. 3. Діти почнуть планувати меню для походу на 6 дітей. 1. Діти дізнаються про харчову піраміду. 2. Діти дізнаються про розмір рекомендованої порції на одну людину. 3. Діти почнуть планувати меню для походу на 6 дітей. Діти почнуть планувати меню для походу на 6 дітей. Ти дізнаєшся про харчові піраміду" +
                                    "Ти дізнаєшся про розмір рекомендованої порції на одну людину" +
                                    "Ти навчишся планувати меню для походу" +
                                    "Ти навчишся розраховувати вартість меню для походу" +
                                    "Ти навчишся обладнувати місце для миття посуду способом «мий, промивай, промивай»" +
                                    "Діти навчаться обладнувати місце для миття посуду способом «помий і двічі сполосни». Ти навчишся обладнувати місце для миття рук\n" +
                                    "Ти навчишся випікати кекси на багатті" +
                                    "Ти навчишся готувати в фользі");
                    testRequestDto.setLogoUrl(null);
                    testRequestDto.setTestType("RED");
                    testRequestDto.setQuarterAchievementId(1);
                    break;
                }
                case 5: {
                    testRequestDto.setName("Розведення багаття");
                    testRequestDto.setShortDescription("Червоний тест майстерності. Ти навчишся розпалювати багаття 6 видів.");
                    testRequestDto.setDescription(
                            "Ти зможеш назвати правила техніки безпеки при розпалюванні багаття." +
                                    "Ти знатимеш про 4 види трута, хмиз та дрова." +
                                    "Ти зможеш показати, як складається багаття Тіпі." +
                                    "Ти зможут зібрати протипожежне обладнання для походу." +
                                    "Ти зможеш пояснити, як боротися з вогнем у різних умовах: вдома, в лісі, на відкритому просторі." +
                                    "Ти зможеш зібрати табірне протипожежне обладнання й пояснити його призначення." +
                                    "Ти зможеш скласти багаття в вигляді літери А." +
                                    "Ти зможеш назвати правила безпеки при поводженні з газовою плиткою." +
                                    "Ти зможеш показати, як складається мисливське багаття та багаття ради." +
                                    "Ти зможеш показати, як складається канавне багаття." +
                                    "Ти навчишся виконувати всі правила безпеки." +
                                    "Ти зможеш показати, як правильно гасити багаття.");
                    testRequestDto.setLogoUrl(null);
                    testRequestDto.setTestType("RED");
                    testRequestDto.setQuarterAchievementId(1);
                    break;
                }
            }
            tests.add(testRequestDto);
        }
        ;
        return tests;
    }
}
