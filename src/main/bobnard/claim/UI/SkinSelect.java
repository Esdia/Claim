package bobnard.claim.UI;

import javax.swing.*;

public class SkinSelect extends JFrame {
        public Boolean selected = false;
        public Object skin;

        public SkinSelect() {

            JList list = new JList(new String[] {"Vanilla", "Umineko"});
            ListSkin ls = new ListSkin("Please select a game skin: ", list);
            ls.setOnOk(e -> {
                if(ls.getSelectedItem() != null) selected = true;
                this.skin = ls.getSelectedItem();
            });

            ls.show();


        }
}
