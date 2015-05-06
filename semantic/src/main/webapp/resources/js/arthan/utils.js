/**
 * Created by arthan on 03.05.15.
 */
(function(sem, $) {
    var utils = {
        param: function (name) {
            return (location.search.split(name + '=')[1] || '').split('&')[0];
        },

        div: function (id, text) {
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
        },

        uList: function (itemTexts) {
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
        },

        anchor: function (href, text) {
            var link = $('<a></a>');

            // ()
            if (arguments.length == 0) {
                return link;
            }

            // (href, text)
            link.prop('href', href);
            link.text(text);

            return link;
        },

        lItem: function () {
            return $('<li></li>');
        }

    };
    utils.id = utils.param('id');
    sem.Utils = utils;
})(Sem, jQuery);