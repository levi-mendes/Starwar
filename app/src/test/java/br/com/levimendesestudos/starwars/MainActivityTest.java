package br.com.levimendesestudos.starwars;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import br.com.levimendesestudos.starwars.activities.MainActivity;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Levi on 04/02/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    @Test
    public void shouldNotBeNull() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        assertNotNull(activity);
    }
}
