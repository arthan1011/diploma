/**
 * Created by arthan on 03.05.15.
 */
(function() {
    Utils = {
        param : function(name) {
            return (location.search.split(name + '=')[1]||'').split('&')[0];
        }
    };
    Utils.id = Utils.param('id');
})();
console.log('arthan utils loaded');