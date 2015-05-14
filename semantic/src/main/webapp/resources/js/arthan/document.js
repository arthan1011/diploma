/**
 * Created by artur.shamsiev on 14.05.2015.
 */

(function(sem) {
    var documentObject = {};

    documentObject.createTabs = function(document) {


        var result = $();
        var tabTitleList = sem.Utils.uList();

        tabTitleList.append(sem.Utils.createTabTitleItem('author', "Автор"));

        result = result.add(tabTitleList);
        result = result.add(sem.Utils.div("author", document['creator']));

        return result;
    };

    documentObject.loadInfo = function(options) {
        function onSuccess(data) {
            var document = data['document'];
            var $tabs = $('#' + options['tabsTargetID']);
            $tabs.append(documentObject.createTabs(document));
            $tabs.tabs();
        }

        documentObject._documentPage = options['documentPage'];

        sem.Utils.sendAjax({
            url: options['url'],
            onSuccess: onSuccess
        });
    };

    sem.Document = documentObject;
})(Sem);