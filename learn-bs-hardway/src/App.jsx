import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Card, Col, Container, Row } from "react-bootstrap";

const App = () => {
  const [theme, setTheme] = React.useState("light");
  const changeTheme = () => {
    const newTheme = theme === "dark" ? "light" : "dark";
    setTheme(newTheme);
  };
  return <Container className="mt-5" fluid="md">
    <Row>
      <Col>
      <Card data-bs-theme={theme}>
        <Card.Body>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Facilis minima non harum ducimus eum commodi iste obcaecati impedit consectetur delectus, fuga voluptatem maiores corrupti, odio, aliquam eveniet voluptatibus optio adipisci!
        </Card.Body>
        <Card.Footer>
          <Button variant="primary" onClick={changeTheme}>Toggle Theme</Button>
        </Card.Footer>
      </Card>
      </Col>
    </Row>
  </Container>
};

export default App;
