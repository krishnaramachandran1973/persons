import axios from "axios";
import { useState } from "react";
import { URL } from "./Constants";

const AddPerson = ({ state, changeState }) => {
  const token = localStorage.getItem("jwt");
  const [formErrors, setFormError] = useState({ name: "", email: "", age: "" });

  const [person, setPerson] = useState({
    name: "",
    email: "",
    age: "",
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    if (Object.keys(formErrors).length === 0) {
      axios
        .post(
          `${URL}/api/persons`,
          {
            name: person.name,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )
        .then(() => {
          changeState(!state);
          setPerson({ name: "", email: "", age: "" });
        });
    }
  };

  // Dynamic Object keys
  const handleChange = (e) => {
    const { name, value } = e.target;
    setPerson({ ...person, [name]: value });
    setFormError(validateForm(person)); // setFormError(errors)
  };

  const validateForm = (person) => {
    const errors = {};

    if (person.name && person.name.length < 5) {
      errors.name = "Please enter a valid name";
    }

    if (
      person.email &&
      !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(person.email)
    ) {
      errors.email = "Please enter a valid email";
    }

    if (person.age.length === 0) {
      errors.age = "Please enter your age";
    }
    return errors;
  };

  return (
    <section className="container border rounded m-5 p-5">
      <h6>Add a Person</h6>
      <form onSubmit={handleSubmit}>
        <div className="form-group mb-2">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            className="form-control"
            id="name"
            name="name"
            placeholder="Enter name"
            value={person.name}
            onChange={handleChange}
          />
          <div className="text-danger">{formErrors.name}</div>
        </div>
        <div className="form-group mb-2">
          <label htmlFor="name">Email</label>
          <input
            type="text"
            className="form-control"
            id="email"
            name="email"
            placeholder="Enter email"
            value={person.email}
            onChange={handleChange}
          />
          <div className="text-danger">{formErrors.email}</div>
        </div>
        <div className="form-group mb-2">
          <label htmlFor="name">Age</label>
          <input
            type="text"
            className="form-control"
            id="age"
            name="age"
            placeholder="Enter age"
            value={person.age}
            onChange={handleChange}
          />
          <div className="text-danger">{formErrors.age}</div>
        </div>
        <button
          type="submit"
          className="btn btn-primary"
          disabled={Object.keys(formErrors).length !== 0}
        >
          Add Person
        </button>
      </form>
    </section>
  );
};

export default AddPerson;
