import React from "react";
import { Flex, Text, Image } from "@chakra-ui/react";
import Logo from "../assests/images/OIP (4).jpeg";

function Navbar() {
  return (
    <Flex
      h={65}
      w={"100%"}
      justifyContent={"space-between"}
      border={"1px solid #bcbcbc"}
      alignItems={"center"}
      pl={5}
      pr={5}
    >
      <Text fontWeight={"semibold"}>Sri Tel</Text>
      <Image w={50} h={50} src={Logo} />
    </Flex>
  );
}

export default Navbar;
