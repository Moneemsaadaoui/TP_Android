package rrdl.tp_android;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.oc.hnapp.android.HNQueryTask;

public class MainActivity extends AppCompatActivity {

    private HNQueryTask _task=null;
    private int _page=0;
    private HNArticleAdapter _adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _adapter=new HNArticleAdapter();
        recyclerView.setAdapter(_adapter);
        _task=new HNQueryTask(_adapter,80,1);
        _task.execute();
        loadnext();
        //loading the startup progress bar
        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.progress);
        _adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
public void loadnext(){
        if(_task!=null &&_task.getStatus()!= AsyncTask.Status.FINISHED)
            return;
        _task=new HNQueryTask(_adapter,80,++_page);
        _task.execute();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        _task.cancel(true);
    }
}
