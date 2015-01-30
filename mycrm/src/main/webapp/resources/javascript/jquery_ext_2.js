(function( $ ) {
    $.widget( "ui.combobox", {
        _create: function() {
            alert("ui.combobox.create");
        },

        _destroy: function() {
            this.wrapper.remove();
            this.element.show();
        }
    });
})( jQuery );