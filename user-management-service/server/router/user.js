const express = require("express");
const router = express.Router();
const User = require("../models/user");
const auth = require("../middleware/auth");

// Document
router.get("/", (req, res) => {
  res.send({ document: "https://github.com/810Teams/sop-reservation-service" });
});

// Create User
router.post("/users", async (req, res) => {
  try {
    const user = new User(req.body);
    await user.save();
    res.status(201).send(user);
  } catch (e) {
    res.status(400).send(e);
  }
});

router.get("/users/me", auth, async (req, res) => {
  res.send(req.user);
});

router.patch("/users/me", auth, async (req, res) => {
  const updates = Object.keys(req.body);
  const allowedUpdates = [
    "username",
    "password",
    "firstname",
    "lastname",
    "role"
  ];
  const isValidOperation = updates.every(update =>
    allowedUpdates.includes(update)
  );


  if (!isValidOperation) {
    return res.status(400).send({ error: "Invalid updates" });
  }

  try {
    const user = req.user;
    updates.forEach(update => {
      user[update] = req.body[update];
    });
    await user.save();
    if (updates.includes("password")) {
      user.logout(req.token);
    }

    res.send(user);
  } catch (e) {
    res.status(400).send(e);
  }
});

router.delete("/users/me", auth, async (req, res) => {
  try {
    console.log("in")
    const user = req.user;
    res.send({status:true});
    await user.remove();
  } catch (e) {
    res.status(500).send();
  }
});

module.exports = router;
