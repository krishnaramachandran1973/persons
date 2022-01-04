const DisplayPerson = ({ id, name, removePerson }) => {
  return (
    <article className="row text-white m-3 text-center">
      <p className="col-6 text-white bg-warning text-center">{name}</p>
      <p className="col-6">
        <button className="btn btn-danger" onClick={() => removePerson(id)}>
          Remove
        </button>
      </p>
    </article>
  );
};

export default DisplayPerson;
