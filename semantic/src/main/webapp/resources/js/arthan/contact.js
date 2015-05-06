/**
 * Created by artur.shamsiev on 06.05.2015.
 */

(function(sem) {
    var contactObject = {
        createTabs: function(contact) {
            var result = $();
            var tabTitleList = Sem.Utils.uList();

            function createTabTitleItem(prop, title) {
                var item = sem.Utils.lItem();
                var link = sem.Utils.anchor('#' + prop, title);
                item.append(link);
                return item;
            }

            function createTabItem(prop, contact) {
                return sem.Utils.div(prop, contact[prop]);
            }

            function createListTabItem(listProp, contact) {
                var tabItem = sem.Utils.div(listProp);
                tabItem.append(sem.Utils.uList(contact[listProp]));
                return tabItem;
            }

            tabTitleList.append(createTabTitleItem('firstName', "Имя"));
            tabTitleList.append(createTabTitleItem('lastName', "Фамилия"));
            tabTitleList.append(createTabTitleItem('emails', "Почта"));
            result = result.add(tabTitleList);

            var selector = createTabItem('firstName', contact);
            result = result.add(selector);
            result = result.add(createTabItem('lastName', contact));
            result = result.add(createListTabItem('emails', contact));

            return result;
        }
    };
    sem.Contact = contactObject;
})(Sem);