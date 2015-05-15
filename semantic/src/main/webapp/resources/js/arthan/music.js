/**
 * Created by artur.shamsiev on 15.05.2015.
 */

(function(sem) {
    var musicObject = {};

    musicObject.createTabs = function(music) {
        var result = $();

        var tabTitleList = sem.Utils.uList();

        tabTitleList.append(sem.Utils.createTabTitleItem('musicTitle', "Название"));
        tabTitleList.append(sem.Utils.createTabTitleItem('album', "Альбом"));
        tabTitleList.append(sem.Utils.createTabTitleItem('genre', "Жанр"));
        tabTitleList.append(sem.Utils.createTabTitleItem('performer', "Исполнитель"));

        result = result.add(tabTitleList);
        result = result.add(sem.Utils.div('musicTitle', music['musicTitle']));
        result = result.add(sem.Utils.div('album', music['album']));
        result = result.add(sem.Utils.div('genre', music['genre']));
        result = result.add(sem.Utils.div('performer', music['performer']));

        return result;
    };

    musicObject.loadInfo = function(options) {

        function onSuccess(data) {
            var music = data['music'];
            var $tabs = $('#' + options['tabsTargetID']);
            $tabs.append(musicObject.createTabs(music));
            $tabs.tabs();
        }

        sem.Utils.sendAjax({
            url: options['url'],
            onSuccess: onSuccess
        });
    };

    sem.Music = musicObject;
})(Sem);
