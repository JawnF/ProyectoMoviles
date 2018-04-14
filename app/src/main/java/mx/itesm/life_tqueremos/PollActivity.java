package mx.itesm.life_tqueremos;

import android.media.tv.TvContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class PollActivity extends AppCompatActivity {

    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        progress = (ProgressBar) findViewById(R.id.poll_progress);

    }
}
