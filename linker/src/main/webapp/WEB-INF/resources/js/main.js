$(function(){
	
	$("#btnallclassif").click(function(){
		$(".navtreeview").slideToggle(200);
	})
	




  var animationSpeed = 300;
  
  $("#navtreeview .sidebar-menu").on('click', 'li a', function(e) {
    var $this = $(this);
    var checkElement = $this.next();

    if (checkElement.is('.treeview-menu') && checkElement.is(':visible')) {
      checkElement.slideUp(animationSpeed, function() {
     
      });
      checkElement.parent("li").removeClass("active");
    }

    else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {

      var parent = $this.parents('ul').first();
 
      var ul = parent.find('ul:visible').slideUp(animationSpeed);

      var parent_li = $this.parent("li");

      checkElement.slideDown(animationSpeed, function() {

        parent.find('li.active').removeClass('active');
        parent_li.addClass('active');
      });
    }

    if (checkElement.is('.treeview-menu')) {
      e.preventDefault();
    }
  });

$("#subnav").on('click', 'li a', function(e) {
    var $this = $(this);
    var checkElement = $this.next();
    
    if (checkElement.is('.treeview-menu') && checkElement.is(':visible')) {
     	checkElement.slideUp(animationSpeed);
     	checkElement.parent("li").removeClass("active");
	}
    else if((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))){
    	var parent = $this.parents('ul').first();
//  	var ul = parent.find('ul:visible').slideUp(animationSpeed);
        var parent_li = $this.parent("li");
        
     	checkElement.slideDown(animationSpeed, function() {

        parent.find('li.active').removeClass('active');
        parent_li.addClass('active');
      });
    }
    else{
    	$(this).parents().find("a.act").removeClass("act");
    	$(this).addClass("act");
    }

//  if (checkElement.is('.treeview-menu') && checkElement.is(':visible')) {
//    checkElement.slideUp(animationSpeed, function() {
//   
//    });
//    checkElement.parent("li").removeClass("active");
//  }
//
//  else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
//
//    var parent = $this.parents('ul').first();
// 
//    var ul = parent.find('ul:visible').slideUp(animationSpeed);
//
//    var parent_li = $this.parent("li");
//
      
//  }
//
//  if (checkElement.is('.treeview-menu')) {
//    e.preventDefault();
//  }
  });





//结束
});