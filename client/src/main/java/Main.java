import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.MainScene;
import view.SceneMaster;

public class Main extends Application {
    public static void main(String[] args) {
        //todo 启动netty

        // 启动窗口
        launch();
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainStage.fxml"));
        Parent root = loader.load();

        // 取得MainStage对应的Controller对象
        MainScene controller = loader.getController();

        // 将本stage传递给MainStageController对象，以便更改窗口尺寸
        controller.setStage(stage);

        // 将MainStageController加进Scene的容器，以便于其他类或对象取得
        SceneMaster.registerScene(controller);

        // 初始化窗口参数
        Scene scene = new Scene(root, 700, 360);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);

        // 显示窗口
        stage.show();
    }
}
