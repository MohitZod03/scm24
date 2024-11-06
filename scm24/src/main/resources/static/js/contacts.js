console.log("it is script from the contacts");

// const baseURL = "http://localhost:8081";
const baseURL = "http://localhost:8081";

// to view the contact modal

    // set the modal menu element // and get from id view_contact_modal
const view_contact_modal = document.getElementById('view_contact_modal');

    // options with default values for modal that how to show and hide
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};


// instance options object
const instanceOptions = {
    id: 'view_contact_modal',
    override: true
  };

  // to show the modal
  const modal = new Modal(view_contact_modal, options, instanceOptions);

 // then create a function to show the modal that use in button where we want to show the modal
 function showModal(){
    modal.show();
 }
 
function hideModal(){
    modal.hide();
}

// this is for the contact id that pass from the button object(c.id)


function getContactId(contactId){
    console.log(contactId);
}

                  //  know we get all the data  and fetch the data from the backend and show in the modal.html

async  function  getContactId(id){
    console.log(id);
   try {
    const data = await (await fetch(`${baseURL}/api/contact/${id}`)).json();

        console.log(data);

// this is for the putting data on the model
      
document.querySelector("#contact_name").innerHTML = data.name;

document.querySelector("#contact_email").innerHTML = data.email;

document.querySelector("#contact_phone").innerHTML = data.phoneNumber;

document.querySelector("#contact_address").innerHTML = data.address;
document.querySelector("#contact_about").innerHTML = data.description;

document.querySelector("#contact_image").src = data.picture;

if(data.favorite){
    document.querySelector("#contact_favorite").innerHTML = "<i class='fas fa-star text-yellow-400'>"   ;
}else{
    document.querySelector("#contact_favorite").innerHTML = "not favorite";
}
    

document.querySelector("#contact_website").innerHTML = data.websiteLink;

document.querySelector("#contact_linkedIn").innerHTML = data.linkedinLink;
// this is for to open the model..
 
      showModal();  

   } catch (error) {
    console.log(error);
   }
  
}





async function deleteContact(id) {
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"    })
        .then((result) => {
     if (result.isConfirmed) {
        const url = `${baseURL}/user/contacts/delete/` + id;
        window.location.replace(url);
      }
    });
 

}



