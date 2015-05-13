/**
 * Created by arthan on 03.05.15.
 */
(function(sem, $) {
    var utils = {};
    utils.param = function (name) {
        return (location.search.split(name + '=')[1] || '').split('&')[0];
    };

    utils.div = function (id, text) {
        var div = $('<div></div>');

        // ()
        if (arguments.length == 0) {
            return div;
        }

        // (id)
        if (text == undefined) {
            div.prop('id', id);
            return div;
        }

        // (id, text)
        div.prop('id', id);
        div.text(text);
        return div;
    };

    utils.uList = function (itemTexts) {
        var list = $('<ul></ul>');

        // ()
        if (arguments.length == 0) {
            return list;
        }

        // (itemTexts)
        for (var i = 0; i < itemTexts.length; i++) {
            var element = itemTexts[i];
            list.append('<li>' + element + '</li>');
        }
        return list;
    };

    utils.anchor = function (href, text) {
        var link = $('<a></a>');

        // ()
        if (arguments.length == 0) {
            return link;
        }

        // (href, text)
        link.prop('href', href);
        link.text(text);

        return link;
    };

    utils.lItem = function () {
        return $('<li></li>');
    };

    utils.sImg = function (url) {
        var img = $('<img></img>');

        if (arguments.length = 0) {
            return img;
        }

        img.prop('src', url);
        img.addClass('sImg');
        return img;
    };

    utils.btn = function (options) {
        var button = $('<button type="button"></button>');

        if (arguments.length == 0) {
            return button;
        }
        if (options.id) {
            button.prop('id', options.id);
        }
        if (options.text) {
            button.text(options.text);
        }
        if (options.onclick) {
            button.on('click', options.onclick);
        }

        return button;
    };

    utils.tInput = function (id) {
        var imagePathInput = $('<input type=text>');
        imagePathInput.prop('id', id);

        return imagePathInput;
    };

    utils.addFileControl = function (options) {
        var imageControls = utils.div(options.id);
        imageControls.append(utils.btn({
                id: 'addImageBtn',
                text: options.btnText,
                onclick: options.onAdd
            })
        );
        imageControls.append(utils.tInput(options.inputID));
        return imageControls;
    };

    utils.createTabTitleItem = function (prop, title) {
        var item = utils.lItem();
        var link = utils.anchor('#' + prop, title);
        item.append(link);
        return item;
    };

    utils.createListTabItem = function (itemsList, listProp) {
        var tabItem;

        // (itemsList)
        if (arguments.length == 1) {
            tabItem = utils.div();
        }
        // (listProp, itemsList)
        if (arguments.length == 2) {
            tabItem = utils.div(listProp);
        }

        tabItem.append(utils.uList(itemsList));
        return tabItem;
    };


    utils.id = utils.param('id');
    sem.Utils = utils;
})(Sem, jQuery);