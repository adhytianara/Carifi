package id.ac.ui.cs.mobileprogramming.adhytia.carifi.opengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class OpenGLActivity extends AppCompatActivity {
    private GLSurfaceView gLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("OpenGL Triangle");
        gLView = new MyGLSurfaceView(this);
        setContentView(gLView);
    }
}