package net.sarangnamu.test_blob;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import net.sarangnamu.common.sqlite.DbManager;
import net.sarangnamu.test_blob.db.DbHelper;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button btn;

    @BindView(R.id.imageView)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btn.setOnClickListener(v -> {
            if (DbHelper.getCount() == 0) {
                Drawable d;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    d = getResources().getDrawable(R.drawable.screen, null);
                } else {
                    d = getResources().getDrawable(R.drawable.screen);
                }

                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] data = stream.toByteArray();

                DbHelper.insert(data);
            }

            byte data[] = DbHelper.getData();
            if (data == null) {
                return ;
            }

            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length) ;
            if (bitmap == null) {
                return ;
            }

            img.setImageBitmap(bitmap);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        DbManager.getInstance().open(this, new DbHelper(this));


    }
}
