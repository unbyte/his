import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.MainScene;
import view.ViewCenter;

public class Main extends Application {

    public static void main(String[] args) {
        // 启动窗口
        launch();
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainStage.fxml"));
        Parent root = loader.load();

        // 取得MainStage对应的Controller对象
        MainScene controller = loader.getController();

        // 将MainStageController加进Scene的容器，以便于其他类或对象取得
        ViewCenter.registerScene(controller);

        // 初始化窗口参数
        Scene scene = new Scene(root, 700, 360);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);

        // 取消掉自动关闭
        Platform.setImplicitExit(false);

        ViewCenter.registerStage("main", stage);

        // 显示窗口
        stage.show();
    }

}
