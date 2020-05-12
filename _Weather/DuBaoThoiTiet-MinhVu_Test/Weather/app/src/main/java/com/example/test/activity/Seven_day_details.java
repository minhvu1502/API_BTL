package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.squareup.picasso.Picasso;

public class Seven_day_details extends AppCompatActivity {
    ImageButton btn_back;
    TextView tv_city, tv_country, tv_temp, tv_status, tv_humility, tv_wind, tv_cloud, tv_day, tv_sunrise, tv_sunset, tv_minmax, tv_goiy;
    ImageView img_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_day_details);
        AnhXa();
        Getdata();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void AnhXa() {
        tv_minmax = (TextView) findViewById(R.id.textView);
        btn_back = (ImageButton) findViewById(R.id.btn_seven_back);
        tv_sunrise = (TextView) findViewById(R.id.tv_sunrise);
        tv_sunset = (TextView) findViewById(R.id.tv_sunset);
        tv_city = (TextView) findViewById(R.id.tv_name);
        tv_country = (TextView) findViewById(R.id.tv_city);
        tv_status = (TextView) findViewById(R.id.tv_line_status);
        tv_temp = (TextView) findViewById(R.id.tv_nhietdo);
        tv_humility = (TextView) findViewById(R.id.tv_doam);
        tv_wind = (TextView) findViewById(R.id.tv_gio);
        tv_cloud = (TextView) findViewById(R.id.tv_may);
        tv_day = (TextView) findViewById(R.id.tv_hours);
        img_status = (ImageView) findViewById(R.id.img_weather);
        tv_goiy = (TextView) findViewById(R.id.tvGoiY);
    }

    private void Getdata() {
        Intent intent = getIntent();
        tv_minmax.setText(intent.getStringExtra("minmax"));
        tv_city.setText(intent.getStringExtra("city_name"));
        tv_country.setText(intent.getStringExtra("country"));
        tv_status.setText(intent.getStringExtra("status"));
        Double a = Double.valueOf(intent.getStringExtra("temp"));
        tv_temp.setText(String.valueOf(a.intValue()) + "°");
        tv_humility.setText("Độ ẩm: " + intent.getStringExtra("doam") + "%");
        tv_wind.setText("Gió: " + intent.getStringExtra("gio") + "m/s");
        tv_cloud.setText("Mây: " + intent.getStringExtra("may") + "%");
        tv_day.setText(intent.getStringExtra("Day"));
        tv_sunrise.setText("Mặt trời mọc: " + intent.getStringExtra("sunrise"));
        tv_sunset.setText("Mặt trời lặn: " + intent.getStringExtra("sunset"));
        String icon = intent.getStringExtra("image");
        Picasso.get().load("http://openweathermap.org/img/wn/" + icon + ".png").into(img_status);
            if(intent.getStringExtra("status").equals("mây cụm")){
                tv_goiy.setText("Chú ý: Thời tiết nhiều mây dễ mưa. Nên mang ô hoặc áo mưa khi ra ngoài. " +
                        "\n -- Thời tiết đẹp để ra ngoài uống cafe với bạn bè.");
            }else if (intent.getStringExtra("status").equals("mây rải rác")){
                tv_goiy.setText("Nhắc nhở: Thời tiết ít mây, nắng chiếu vừa ra ngoài nên mang theo mũ và áo chống nắng để bảo vệ cơ thể" +
                        "\n -- Trời đẹp dành cho những chuyến picnic và dã ngoại, thích hợp với những buổi tham quan và đi chơi xa.");
            }else if (intent.getStringExtra("status").equals("bầu trời quang đãng")){
                tv_goiy.setText("Chú ý: THời tiết ít mây đôi lúc không có, nắng chiếu gay gắt. " +
                        "\n -- Đề nghị: Hạn chế ra ngoài, nếu ra ngoài cần trang bị đầy đủ(áo, mũ, áo trùm, che mặt...) tránh ánh nắng chiếu trực tiếp." +
                        "\n -- Nắng lớn thích hợp với việc ở trong nhà làm việc và giặt giũ đồ dùng.");
            }else if (intent.getStringExtra("status").equals("mưa vừa")){
                tv_goiy.setText("Chú ý: Thời tiết có mưa, mưa thường xuyên. " +
                        "\n -- Ra ngoài nên mang theo áo mưu hoặc ô, ưu tiên di chuyển trên các phương tiện có mái che(xe bus, ô tô, ...) " +
                        " \n -- Thời tiết thích hợp với việc ở nhà nấu ăn và thực hiện các sở thích có thể làm trong nhà.");
            }else if (intent.getStringExtra("status").equals("mưa cường độ nặng")){
                tv_goiy.setText("Chú ý: Mưa to, mưa lớn không thích hợp ra ngoài nên hạn chế ra ngoài tối đa. Nếu cần di chuyển ưu tiên " +
                        " các phương tiện có mái che(xe bus, ô tô, ...).");
            }else if (intent.getStringExtra("status").equals("mưa nhẹ")){
                tv_goiy.setText("Nhắc nhở: Thời tiết có mưa, mưa lâm thâm, ra ngoài nên mang theo ô. " +
                        "\n -- Thời tiết thích hợp đi dạo, đi chơi với bạn bè");
            }else if (intent.getStringExtra("status").equals("mây đen u ám")){
                tv_goiy.setText("Chú ý: Trời râm, rất nhiều mây, có khả năng mưa đột ngột. Đề nghị mang theo ô hoặc áo mưa khi ra ngoài" +
                        "\n -- Hạn chế ra đường vì có thể mưa bất chợt từ mưa vừa tới mưa to. " +
                        "\n -- Ưu tiên làm các buối party tại nhà.");
            }else if (intent.getStringExtra("status").equals("mây thưa")){
                tv_goiy.setText("Nhắc nhở: Thời tiết ít mây, nắng chiếu vừa ra ngoài nên mang theo mũ và áo chống nắng để bảo vệ cơ thể" +
                        "\n -- Trời đẹp dành cho những chuyến picnic và dã ngoại, thích hợp với những buổi tham quan và đi chơi xa.");
            }else {
                tv_goiy.setText("Chú ý thời tiết khi ra ngoài và mang đầy đủ các dụng cụ cá nhân!!!");
            }
    }
}
