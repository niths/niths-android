package main.java.no.niths.views;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import main.java.no.niths.views.activities.MainActivity;
import no.niths.android.R;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Test
    public void shouldHaveProperAppName() throws Exception{
        String appName = new MainActivity().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("Niths fadderuke"));
    }
}