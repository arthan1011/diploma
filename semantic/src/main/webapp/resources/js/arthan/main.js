/**
 * Created by artur.shamsiev on 13.05.2015.
 */

(function(sem) {
    var mainObject = {};

    mainObject.createTabs = function(main) {
        function createContactsTabItem(contactProp, contacts) {
            var tabItem = Sem.Utils.createListTabItem(
                contacts.map(function (item, id, arr) {
                    return item['firstName'] + ' ' + item['lastName'];
                }),
                contactProp
            );
            return tabItem;
        }

        var result = $();
        var tabTitleList = Sem.Utils.uList();

        tabTitleList.append(sem.Utils.createTabTitleItem('contacts', "Контакты"));

        result = result.add(tabTitleList);
        result = result.add(createContactsTabItem('contacts', main['contacts']));

        return result;
    };

    mainObject.loadInfo = function(options) {
        $.ajax({
            type: 'GET',
            url: options['url'],
            headers: {
                Accept : "text/plain; charset=utf-8"
            },
            dataType: 'json',
            success: function(data, status, jqXHR) {
                var main = data['main'];
                var $tabs = $('#' + options['tabsTargetID']);
                $tabs.append(mainObject.createTabs(main));
                $tabs.tabs();

            }
        });
    };
    sem.Main = mainObject;
})(Sem);