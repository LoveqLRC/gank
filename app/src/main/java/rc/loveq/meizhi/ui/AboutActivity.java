package rc.loveq.meizhi.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rc.loveq.meizhi.BuildConfig;
import rc.loveq.meizhi.R;
import rc.loveq.meizhi.ui.base.BaseActivity;
import rc.loveq.meizhi.util.Shares;



public class AboutActivity extends BaseActivity {

    @BindView(R.id.version)
    TextView mVersion;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setupVersionName();
        mCollapsingToolbar.setTitle("Liao");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setupVersionName() {
        mVersion.setText("Version:"+ BuildConfig.VERSION_NAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_share:
                Shares.share(AboutActivity.this,R.string.share_text);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
