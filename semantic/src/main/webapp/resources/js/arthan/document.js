/**
 * Created by artur.shamsiev on 14.05.2015.
 */

(function(sem) {
    var documentObject = {};

    documentObject.createTabs = function(document) {

        function createAuthorTabItem(prop, creator) {
            var link = sem.Utils.anchor(
                documentObject._contactPage + '?id=' + creator['id'],
                creator['firstName'] + ' ' + creator['lastName']
            );

            var item = sem.Utils.div(prop);
            item.append(link);
            return item;
        }

        var result = $();

        var tabTitleList = sem.Utils.uList();

        tabTitleList.append(sem.Utils.createTabTitleItem('author', "Автор"));
        result = result.add(tabTitleList);

        result = result.add(createAuthorTabItem('author', document['creator']));

        return result;
    };

    documentObject.loadInfo = function(options) {
        function onSuccess(data) {
            var document = data['document'];
            var $tabs = $('#' + options['tabsTargetID']);
            $tabs.append(documentObject.createTabs(document));
            $tabs.tabs();
        }

        documentObject._contactPage = options['contactPage'];

        sem.Utils.sendAjax({
            url: options['url'],
            onSuccess: onSuccess
        });
    };

    sem.Document = documentObject;
})(Sem);