function eliminar() {
    swal({
        title: "Estás seguro de eliminar el usuario?",
        text: "Al eliminar el usuario no habra vuelta atras",
        icon: "warning",
        buttons: true,
        dangerMode: true,
      })
      .then((willDelete) => {
        if (willDelete) {
          swal("Ha eliminado el usuario", {
            icon: "success",
          });
        } else {
          swal("Se ha cancelado");
        }
      });
}