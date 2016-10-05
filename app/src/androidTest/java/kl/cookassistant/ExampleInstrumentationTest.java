package kl.cookassistant;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.List;

import kl.cookassistant.DataModel.DTMap;
import kl.cookassistant.DataModel.DUMap;
import kl.cookassistant.DataModel.DBhelper;
import kl.cookassistant.DataModel.Dish;
import kl.cookassistant.DataModel.Tag;
import kl.cookassistant.DataModel.User;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentationTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBhelper da = new DBhelper(appContext);

/*        da.insertNewUser("kevin", "password");
        da.insertNewDish("potato", "des", true);*/

        /*da.insertNewDishUserMap(1,1);*/

        da.deleteUser("kevin", "password");

        List<User> users = da.getAllUsers();
        List<Tag> tags = da.getAllTags();
        List<Dish> Dishes = da.getAllDishes();
        List<DUMap> DUMaps = da.getAllDUMap();
        List<DTMap> DTMaps = da.getAllDTMap();
        assertEquals("kl.cookassistant", appContext.getPackageName());
    }
}