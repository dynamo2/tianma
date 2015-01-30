function addToCategory($dragItem,$olItem){
    var $newItem = $("<li></li>").html($dragItem.html()).appendTo($olItem);
    $dragItem.remove();
    $olItem.removeClass("ui-state-hover");
}

function resortCategories(){
    $.each($(".nav_category").find("ol"),function(n,value) {
        var cid = $(value).children("input").val();
        $(".navCategorySeqNumber"+cid).val(n+1);

        $.each($(value).children("li"),function(fsn,item) {
            var fid = $(item).children("input").val();

            $(".navFormSeqNumber"+fid).val(fsn+1);
            $(".navFormCategory"+fid).val(cid);
        });
    });
}

function editCategoryLabel(item){
    var $edit = $(item);
    var $link = $edit.parent().parent().children(".preCategoryLabelLink").hide();
    var $input = $edit.parent().parent().children(".preCategoryLabelInput").show().focus();
    var $save = $edit.parent().children(".preCategoryLabelSave").show();


    $edit.hide();
    $link.hide();
    $input.val($link.text()).show.focus();
    $save.show();
}

function saveCategoryLabel2(cid,item){
    jsf.ajax.request(this, event, {execute:'reset', render: 'out1'});

    var $save = $(item);
    var $link = $save.parent().parent().children(".preCategoryLabelLink");
    var $input = $save.parent().parent().children(".preCategoryLabelInput");
    var $edit = $save.parent().children(".preCategoryLabelEdit");
    var cid = $save.parent().parent().parent().parent().children("div").children("ol").children("input").val();
    $(".navCategoryLabel"+cid).text($input.val());

    $link.text($input.val()).show();
    $edit.show();
    $input.hide();
    $save.hide();
}

function saveCategoryLabel(item){
    var $save = $(item);
    var $link = $save.parent().parent().children(".preCategoryLabelLink");
    var $input = $save.parent().parent().children(".preCategoryLabelInput");
    var $edit = $save.parent().children(".preCategoryLabelEdit");
    var cid = $save.parent().parent().parent().parent().children("div").children("ol").children("input").val();
    $(".navCategoryLabel"+cid).text($input.val());

    $link.text($input.val()).show();
    $edit.show();
    $input.hide();
    $save.hide();
}

function deleteCategory(item){
    var $categoryAccordionDiv = $(item).parent().parent().parent().parent();
    var $liList = $categoryAccordionDiv.children("div").children("ol").children("li");
    var liLength = $liList.length;
    var confirmMsg = "您确定要删除该分类么？";
    if(liLength > 0){
        confirmMsg = "该分类下有"+liLength+"个关联表单，删除分类会解除关联关系。\n"+confirmMsg;
    }

    if(!confirm(confirmMsg)){
        return;
    }

    $liList.appendTo($(".nav_form").children("ul"));
    $categoryAccordionDiv.remove();
}

function ignoreEdit(item){
    var $input = $(item);
    var $link = $input.parent().children(".preCategoryLabelLink");
    var $edit = $input.parent().children("div").children(".preCategoryLabelEdit");
    var $save = $input.parent().children("div").children(".preCategoryLabelSave");

    $edit.show();
    $link.show();
    $input.val($link.text()).hide();
    $save.hide();
}

function addCategoryDiv(clabel){
    $(".nav_category").accordion("destroy");

    var $defaultCategory = $(".nav_category").children(".defaultCategoryAccordion");
    var newCategoryHtml = $defaultCategory.html();
    var $newCategory = $("<div></div>").html(newCategoryHtml).addClass("categoryAccordion").appendTo($(".nav_category"));

    $newCategory.find(".preCategoryLabelLink").text(clabel);
    $newCategory.find(".preCategoryInput").addClass("waitingCategoryId");

    categoryAccordionDroppable($newCategory);

    $(".nav_category").accordion({
            event: "click",
            header:"> div > h2",
            heightStyle: "content"
    });
}

function saveNewCategory(){
    var clabel = $(".newCategoryLabel").val();
    addCategoryDiv(clabel);

    addNavCategoryFormDialog.hide();
}

function categoryAccordionDroppable($category){
    $category.droppable({
        accept:"li",
        over: function( event, ui ) {
            $(this).children("h2").triggerHandler("click");
            $(this).children("div").children("ol").addClass("ui-state-hover");
        },
        out : function( event, ui ) {
            $(this).children("div").children("ol").removeClass("ui-state-hover");
        },
        drop: function( event, ui ) {
            addToCategory(ui.draggable,$(this).children("div").children("ol"));
        }
    }).sortable({
        items: "li",
        sort: function() {
            // gets added unintentionally by droppable interacting with sortable
            // using connectWithSortable fixes this, but doesn't allow you to customize active/hoverClass options
            $( this ).removeClass( "ui-state-default" );
        }
    });
}

function iconHover($icon){
    $icon.hover(
        function() {
            $( this ).addClass( "nav-state-hover" );
        },
        function() {
            $( this ).removeClass( "nav-state-hover" );
        }
    );
}


function initNavigation(){
    $(".nav_category").accordion({
        event: "click",
        header:"> div > h2",
        heightStyle: "content"
    }).sortable({
        items: "div.categoryAccordion"
    });

    categoryAccordionDroppable($(".nav_category div.categoryAccordion"));

    $( ".nav_form li" ).draggable({
        appendTo: "body",
        helper: "clone",
        cursor: "move"
    });

    $(".nav_form").droppable({
        accept:"li",
        activeClass: "ui-state-default",
        hoverClass: "ui-state-hover",
        drop: function( event, ui ) {
            $("<li></li>").html(ui.draggable.html()).addClass("ui-draggable").draggable({
                        appendTo: "body",
                        helper: "clone",
                        cursor: "move"
            }).appendTo($(this).find("ul"));
            ui.draggable.remove();
        }
    });

    iconHover($( ".preCategoryLabelEdit" ));
    iconHover($( ".preCategoryLabelSave" ));
    iconHover($( ".preCategoryLabelDelete" ));
    iconHover($( ".preCategoryLabelAdd" ));
    iconHover($( ".preSystemNavFormAdd" ));
}