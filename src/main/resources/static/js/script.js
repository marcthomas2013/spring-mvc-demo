$(document).ready(
			function() {
				$('#personForm').submit(
						function(event) {
							$('#successMessage').css('display', 'none');
							$('#nameError').css('display', 'none');
							$('#dateOfBirthError').css('display', 'none');
							$('#numberOfChildrenError').css('display', 'none');
							var name = $('#name').val();
							var dateOfBirth = $('#dateOfBirth').val();
							var numberOfChildren = $('#numberOfChildren').val();
							
							var data = 'name='
									+ encodeURIComponent(name)
									+ '&dateOfBirth='
									+ encodeURIComponent(dateOfBirth)
									+ '&numberOfChildren='
									+ encodeURIComponent(numberOfChildren);
							$.ajax({
								url : $("#personForm").attr("action"),
								data : data,
								type : "post",

								success : function(response) {
									$('#successMessage').css('display', 'inline');
								},
								error : function(xhr, status, error) {
									var response = JSON.parse(xhr.responseText);
									for (var i = 0; i < response.validationErrors.length; i++) {
										$('#' + response.validationErrors[i] + 'Error').css('display', 'inline');
									}
								}
							});
							return false;
						});
			});