/**
 * Created by artur.shamsiev on 15.05.2015.
 */

(function(sem) {
    var mailObject = {};

    mailObject.createTabs = function(mail) {

        function createMailBodyTabItem(bodyProp, body) {
            function showMessage() {
                var preview = window.open("" ,"", "width=800, height=900");
                preview.document.write(body);
                preview.document.close();
            }

            var tabItem = sem.Utils.div(bodyProp);
            var button = sem.Utils.btn({
                text: 'Показать собщение',
                onclick: showMessage
            });
            tabItem.append(button);
            return tabItem;
        }

        var result = $();

        var tabTitleList = sem.Utils.uList();

        tabTitleList.append(sem.Utils.createTabTitleItem('from', "Отправитель"));
        tabTitleList.append(sem.Utils.createTabTitleItem('date', "Дата"));
        tabTitleList.append(sem.Utils.createTabTitleItem('subject', "Тема письма"));
        tabTitleList.append(sem.Utils.createTabTitleItem('body', "Сообщение"));

        result = result.add(tabTitleList);
        result = result.add(sem.Utils.div('from', mail['from']));
        result = result.add(sem.Utils.div('date', mail['date']));
        result = result.add(sem.Utils.div('subject', mail['subject']));
        result = result.add(createMailBodyTabItem('body', mail['body']));

        return result;
    };

    mailObject.loadInfo = function(options) {

        function onSuccess(data) {
            var mail = data['mail'];
            var $tabs = $('#' + options['tabsTargetID']);
            $tabs.append(mailObject.createTabs(mail));
            $tabs.tabs();
        }

        sem.Utils.sendAjax({
            url: options['url'],
            onSuccess: onSuccess
        });
    };

    sem.Mail = mailObject;
})(Sem);
