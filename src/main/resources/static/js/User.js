function InsertBotData(Obj) {
	//alert("Insert");
	window.location.href = "http://localhost:9082/BotNameValues/InsertBot";
}
/* Added by 'Vaibhav'*/
function CreateXLSSHEETData(Obj) {
	//alert("Insert");
	window.location.href = "http://localhost:9082/BotNameValues/excel/export";
}
function LoadDataById(Obj) {
	
	
	const params = new Proxy(new URLSearchParams(window.location.search), {
		get: (searchParams, prop) => searchParams.get(prop),
	});

	let value = params.id;
	//alert(value);
}


function importExcelFile2() {
            var fileInput = document.getElementById('fileInput');
            var formData = new FormData();
            formData.append('BotData', fileInput.files[0]);

            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'http://localhost:9082/BotNameValues/importExcel', true);
          
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        alert("Excel file imported successfully.");
                   window.location.href="http://localhost:9082/Dashboard";
                    } else {
                        alert("File cannot be left blank!!");
                    }
                }
            };
            xhr.send(formData);
        }
        
function loadCreate(Obj) {
	window.location.href = "http://localhost:9082/BotNameValues/InsertBot";
}


function editData2(Obj) {
	///alert("Edit");
	var id = Obj.parentNode.parentNode.childNodes[0].innerHTML;
	var postData = {
		"BotId": id
	}
	//alert(id);

	window.location.href = "http://localhost:9082/BotNameValues/EditUser?id=" + id;


}
function StartBot(Obj) {
	///alert("Edit");
	var id = Obj.parentNode.parentNode.childNodes[0].innerHTML;
	var postData = {
		"BotId": id
	}
	//alert(id);

	window.location.href = "http://localhost:9082/BotNameValues/StartBot?id=" + id;


}


function DeleteData(Obj) {

	if (confirm("Are You Sure You want to Delete?") == true) {
		//alert(Obj.parentNode.parentNode.childNodes[0].innerHTML);
		var _id = Obj.parentNode.parentNode.childNodes[0].innerHTML;
		//alert(_id);

		var postData = {
			"BotId": _id
		}
		//alert(postData);

		$.ajax({
			url: "http://localhost:9082/BotNameValues/lPostDeletBotData2",
			type: "POST",
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			data: JSON.stringify(postData),
			success: function(resultB) {
				alert("Deleted Sucessfully !!");
				window.location.reload();
				document.getElementById("txtBotName").value = result[0].BotName;


			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert(errorThrown);
				alert(jqXHR.status + ' ' + jqXHR.responseText);
			}
		})

	} 

}


function InvokeBot(Obj) {
var _varname = window.sessionStorage.getItem('sessionname');
	//alert(_varname);
	document.getElementById("SpnSession").innerHTML = _varname;

	$.ajax({
		type: 'GET',
		url: 'http://localhost:9082/BotNameValues/GetBotListData',
		dataType: 'json',
		async: true,
		success: function(result) {
			// alert(result[0]);
			// console.log(result[0]);

			// window.location.href = "http://localhost:9082/Dashboard1";
			// New record
			$('a.editor-create').on('click', function(e) {
				e.preventDefault();

				editor.create({
					title: 'Create new record',
					buttons: 'Add'
				});
			});

			// Edit record
			$('#BotGridData_dt').on('click', 'td.editor-edit', function(e) {
				e.preventDefault();

				editor.edit($(this).closest('tr'), {
					title: 'Edit record',
					buttons: 'Update'
				});
			});

			// Delete a record
			$('#BotGridData_dt').on('click', 'td.editor-delete', function(e) {
				e.preventDefault();

				editor.remove($(this).closest('tr'), {
					title: 'Delete record',
					message: 'Are you sure you wish to remove this record?',
					buttons: 'Delete'
				});
			});
				// Start a record
			$('#BotGridData_dt').on('click', 'td.editor-start', function(e) {
				e.preventDefault();

				editor.start($(this).closest('tr'), {
					title: 'strt record',
					
					buttons: 'Start'
				});
			});

			$('#BotGridData_dt').DataTable({
				data: result,
				columns: [
					{ data: 'Id' },
					{ data: 'Username' },
					{ data: 'Role Name' },
					{ data: 'Created Date' },
					{ data: 'Created By' },
					{ data: 'CreatedBy' },
					{ data: 'UpdatedDate' },
					{ data: 'UpdatedBy' },
					{ data: 'Edit' },
					{ data: 'Delete' },
					
					{
						data: null,
						className: "dt-center editor-edit",
						defaultContent: '<a type="button" class="btn btn-primary"  data-toggle="modal" data-target="#editMemberModal"  onclick=" editData2(this)"> <span class="glyphicon glyphicon-edit"></span>Edit</a>',
						//defaultContent: '<a href="#"><span style="color:#1cc4d5; font-size:20px;"><i class="fa fa-edit pl-3 pr-3"></i></span></a>',
						orderable: false
					},
					{
						data: null,
						className: "dt-center editor-delete",
						defaultContent: '<a type="button" class="btn btn-primary"  data-toggle="modal" data-target="#editMemberModal"  onclick="DeleteData(this)"> <span class="glyphicon glyphicon-edit"></span>Delete</a>',
						orderable: false
					}
				
				],
			});

		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});



}


