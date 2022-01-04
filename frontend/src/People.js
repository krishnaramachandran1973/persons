import { useState, useEffect } from "react";
import DisplayPerson from "./DisplayPerson";
import axios from "axios";
import AddPerson from "./AddPerson";
import { URL } from "./Constants";
import { useNavigate } from "react-router-dom";

// fetch
// axios
// useEffect -> any side effects

const People = () => {
  const token = localStorage.getItem("jwt");
  const navigate = useNavigate();
  const [people, setPeople] = useState([]);
  const [stateChanged, setStateChanged] = useState(false);

  useEffect(() => {
    if (token) {
      axios
        .get(`${URL}/api/persons`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          setPeople(response.data);
        });
    } else {
      navigate("/", { replace: true });
    }
  }, [stateChanged]);

  const removePerson = (id) => {
    axios
      .delete(`${URL}/api/persons/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then(() => {
        setStateChanged(!stateChanged);
      });
  };

  return (
    <>
      <section className="container border rounded m-5 p-3">
        {people.map((person) => {
          return (
            <DisplayPerson
              key={person.id}
              {...person}
              removePerson={removePerson}
            />
          );
        })}
      </section>
      <AddPerson state={stateChanged} changeState={setStateChanged} />
    </>
  );
};

export default People;
