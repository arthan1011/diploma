/**
 * Created by artur.shamsiev on 13.05.2015.
 */

(function(sem) {
    var mainObject = {};

    mainObject.createTabs = function(main) {
        function createContactsTabItem(contactProp, contacts) {
            function createContactListItem(contact) {
                var link = Sem.Utils.anchor();
                var contactHref = mainObject._contactPage + '?id=' + contact.id;
                link.prop('href', contactHref);
                link.text(contact['firstName'] + ' ' + contact['lastName']);
                return link[0].outerHTML;
            }

            var tabItem = Sem.Utils.createListTabItem(
                contacts.map(function (item, id, arr) {
                    return createContactListItem(item);
                }),
                contactProp
            );
            return tabItem;
        }

        function createDocumentsTabItem(docProp, documents) {
            return sem.Utils.createListTabItem(
                documents.map(function (item, id, arr) {
                    return item['title'];
                }),
                docProp
            );
        }

        var result = $();
        var tabTitleList = Sem.Utils.uList();

        tabTitleList.append(sem.Utils.createTabTitleItem('contacts', "Мои контакты"));
        tabTitleList.append(sem.Utils.createTabTitleItem('documents', "Мои документы"));

        result = result.add(tabTitleList);
        result = result.add(createContactsTabItem('contacts', main['contacts']));
        result = result.add(createDocumentsTabItem('documents', main['documents']));

        return result;
    };

    mainObject.loadInfo = function(options) {
        mainObject._contactPage = options['contactPage'];
        
        function onSuccess(data) {
            var main = data['main'];
            var $tabs = $('#' + options['tabsTargetID']);
            $tabs.append(mainObject.createTabs(main));
            $tabs.tabs();
        }

        sem.Utils.sendAjax({
            url: options['url'],
            onSuccess: onSuccess
        });
    };
    sem.Main = mainObject;
})(Sem);