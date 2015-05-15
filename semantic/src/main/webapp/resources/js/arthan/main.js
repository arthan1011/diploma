/**
 * Created by artur.shamsiev on 13.05.2015.
 */

(function(sem) {
    var mainObject = {};

    mainObject.createTabs = function(main) {
        function createContactsTabItem(contactProp, contacts) {
            function contactListItem(contact) {
                var link = sem.Utils.anchor(
                    mainObject._contactPage + '?id=' + contact.id,
                    contact['firstName'] + ' ' + contact['lastName']
                );
                return link[0].outerHTML;
            }

            var tabItem = sem.Utils.createListTabItem(
                contacts.map(function (item, id, arr) {
                    return contactListItem(item);
                }),
                contactProp
            );
            return tabItem;
        }

        function createDocumentsTabItem(docProp, documents) {
            function documentListItem(document) {
                var link = sem.Utils.anchor(
                    mainObject._documentPage + "?id=" + document['path'],
                    document['title']
                );
                return link[0].outerHTML;
            }

            return sem.Utils.createListTabItem(
                documents.map(function (item, id, arr) {
                    return documentListItem(item);
                }),
                docProp
            );
        }

        function createMusicsTabItem(musicProp, musics) {

            function musicListItem(music) {
                var link = sem.Utils.anchor(
                    mainObject._musicPage + "?id=" + music['path'],
                    music['musicTitle']
                );
                return link[0].outerHTML;
            }

            return sem.Utils.createListTabItem(
                musics.map(function (item, id, arr) {
                    return musicListItem(item);
                }),
                musicProp
            )
        }

        var result = $();
        var tabTitleList = sem.Utils.uList();

        tabTitleList.append(sem.Utils.createTabTitleItem('contacts', "Мои контакты"));
        tabTitleList.append(sem.Utils.createTabTitleItem('documents', "Мои документы"));
        tabTitleList.append(sem.Utils.createTabTitleItem('musics', "Моя музыка"));

        result = result.add(tabTitleList);
        result = result.add(createContactsTabItem('contacts', main['contacts']));
        result = result.add(createDocumentsTabItem('documents', main['documents']));
        result = result.add(createMusicsTabItem('musics', main['musics']));

        return result;
    };

    mainObject.loadInfo = function(options) {
        mainObject._contactPage = options['contactPage'];
        mainObject._documentPage = options['documentPage'];
        mainObject._musicPage = options['musicPage'];

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