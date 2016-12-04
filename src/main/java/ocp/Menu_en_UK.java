package ocp;

import java.util.ListResourceBundle;

/**
 * Created by williaz on 12/4/16.
 */
public class Menu_en_UK extends ListResourceBundle{

    @Override
    protected Object[][] getContents() {
        return new Object[][] { {"Options", "Fish and Fries"} , {"Bus", new Bus("London")} };
    }

    public static class Bus {
        String name;

        public Bus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
