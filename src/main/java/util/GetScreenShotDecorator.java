package util;

import static util.DateTimeConvertor.getCurrentDateTimeForScreenshot;

//Decorator
public class GetScreenShotDecorator extends GetScreenShot{
    protected GetScreenShot decoratedGetScreenShot;

    public GetScreenShotDecorator(GetScreenShot decoratedGetScreenShot) {
        this.decoratedGetScreenShot = decoratedGetScreenShot;
    }

    @Override
    public String capture(String name) {
        return decoratedGetScreenShot.capture(name + "_" + getCurrentDateTimeForScreenshot());
    }
}
