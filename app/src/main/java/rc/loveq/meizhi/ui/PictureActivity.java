package rc.loveq.meizhi.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import rc.loveq.meizhi.R;
import rc.loveq.meizhi.ui.base.ToolBarActivity;
import rc.loveq.meizhi.util.RxMeizhi;
import rc.loveq.meizhi.util.Shares;
import rc.loveq.meizhi.util.Toasts;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PictureActivity extends ToolBarActivity {
    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRAL_TITLE = "extra_title";
    public static final String TRANSITION_PIC = "transition_pic";
    public String mExtraTitle;
    public String mExtraUrl;

    @BindView(R.id.picture)
    ImageView mImageView;
    public PhotoViewAttacher mAttacher;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture;
    }

    public static Intent newIntent(Context context,String extraUrl, String extralTitle){
        Intent intent=new Intent(context,PictureActivity.class);
        intent.putExtra(EXTRA_URL,extraUrl);
        intent.putExtra(EXTRAL_TITLE,extralTitle);
        return  intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mExtraTitle = getIntent().getStringExtra(EXTRAL_TITLE);
        mExtraUrl = getIntent().getStringExtra(EXTRA_URL);
        ViewCompat.setTransitionName(mImageView,TRANSITION_PIC);
//        Glide.with(PictureActivity.this).load(mExtraUrl).into(mImageView);
        Picasso.with(this).load(mExtraUrl).into(mImageView);
        setAppBarAlpha(0.7f);
        setTitle(mExtraTitle);
        setupPhotoAttacher();
    }

    private void setupPhotoAttacher() {
        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                hideOrShowToolBar();
            }
        });
        mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(PictureActivity.this)
                        .setMessage(getString(R.string.ask_saving_picture))
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {
                                anInterface.dismiss();
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {
                                anInterface.dismiss();
                                saveToGallery();
                            }
                        }).show();
                return true;
            }
        });

    }

    private void saveToGallery() {
        Subscription subscription = RxMeizhi.saveImageAndGetPathObservable(PictureActivity.this, mExtraUrl, mExtraTitle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        File file = new File(Environment.getExternalStorageDirectory(), "Liao");
                        String s = String.format(getString(R.string.picture_has_save_to), file.getAbsolutePath());
                        Toasts.showLong(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toasts.showLong(throwable.getMessage() + "\n 再试试");

                    }
                });
        addSubscription(subscription);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                RxMeizhi.saveImageAndGetPathObservable(this,mExtraUrl,mExtraTitle)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Uri>() {
                            @Override
                            public void call(Uri uri) {
                                Shares.shareImage(PictureActivity.this, uri, mExtraTitle);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Toasts.showLong(throwable.getMessage());
                            }
                        });
                return true;
            case R.id.action_save:
                saveToGallery();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAttacher.cleanup();
    }


}
