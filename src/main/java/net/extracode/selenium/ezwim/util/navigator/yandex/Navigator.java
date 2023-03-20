package net.extracode.selenium.ezwim.util.navigator.yandex;

public class Navigator {

    private static Navigator instance;

    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }


    private YANDEX yandex;

    private Navigator() {

        yandex = new YANDEX();
    }


    public YANDEX getYandex() {
        return yandex;
    }
}
