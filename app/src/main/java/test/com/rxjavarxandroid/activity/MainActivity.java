package test.com.rxjavarxandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import test.com.rxjavarxandroid.R;
import test.com.rxjavarxandroid.comman.RxjavaRetrofitApplication;
import test.com.rxjavarxandroid.dao.DaoUtils;
import test.com.rxjavarxandroid.entity.ApiResult;
import test.com.rxjavarxandroid.entity.ChannleEntity;
import test.com.rxjavarxandroid.exception.ApiException;
import test.com.rxjavarxandroid.subscriber.SubscriberListener;
import test.com.rxjavarxandroid.utils.HttpLog;
import test.com.rxjavarxandroid.utils.ToastUtil;


public class MainActivity extends AppCompatActivity {

    private String tag="MainActivity";

    Button clickMeBN;
    TextView resultTV;
    String url="http://api.newsapp.cnr.cn/news/getChannelList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        clickMeBN=(Button)findViewById(R.id.click_me_BN);
        resultTV=(TextView)findViewById(R.id.result_TV);

        clickMeBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ChannleEntity>  ChannleEntityList=  DaoUtils.getChannleInstance().QueryAll(ChannleEntity.class);
                if(ChannleEntityList!=null&&ChannleEntityList.size()>0){
                    resultTV.setText(ChannleEntityList.toString());
                }else {

                    RxjavaRetrofitApplication.getInstance().getDomyShowService().getChannleList(new SubscriberListener<ApiResult<List<ChannleEntity>>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(ApiException e) {
                            ToastUtil.show(e.message);
                        }

                        @Override
                        public void onNext(ApiResult<List<ChannleEntity>> listApiResult) {
                            updata(listApiResult);
                        }
                    }, RxjavaRetrofitApplication.getInstance(), url, null);
                }
            }
        });


    }

    public void updata(ApiResult<List<ChannleEntity>> listApiResult){
        List<ChannleEntity> list=listApiResult.getData();
        DaoUtils.getChannleInstance().insertMultObject(list);
        resultTV.setText(DaoUtils.getChannleInstance().QueryAll(ChannleEntity.class).toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
