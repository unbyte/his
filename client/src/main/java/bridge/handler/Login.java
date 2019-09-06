package bridge.handler;

import view.MainScene;
import view.SceneMaster;

class Login extends RequestHandler {

    @Override
    public void before() {

    }

    @Override
    public void after() {
        /*get MainScene object from container of scenes*/
        MainScene mainScene = SceneMaster.getScene("MainScene");

        /*change size*/
        mainScene.changeSize(1280, 860);
    }
}
