import React from "react";
import {
	Card,
	CardHeader,
	Heading,
	CardBody,
	Text,
	CardFooter,
	Button,
} from "@chakra-ui/react";

function Service({ myService = true, serviceDetails, arrIndex, openPopup }) {
	return (
		<Card border={"1px"} borderColor={"blackAlpha.400"}>
			<CardHeader>
				<Heading size="lg" color={"#333"} textAlign={"center"}>
					{serviceDetails.name}
				</Heading>
			</CardHeader>
			<CardBody py={0}>
				<Text as={"b"} color={"#444"} fontSize={"12px"}>
					Description
				</Text>
				<Text ml={3} mb={1}>
					{serviceDetails.description}
				</Text>
				<Text as={"b"} color={"#444"} fontSize={"12px"}>
					Valid Time Duration
				</Text>
				<Text ml={3} mb={1}>
					{serviceDetails.validTimeDuration}
				</Text>
				<Text as={"b"} color={"#444"} fontSize={"12px"}>
					Service Charge
				</Text>
				<Text ml={3}>{serviceDetails.serviceCharge}</Text>
			</CardBody>
			<CardFooter>
				<Button
					colorScheme={myService ? "red" : "green"}
					onClick={() => {
						openPopup(arrIndex, myService);
					}}
					w={"100%"}
				>
					{myService ? "Deactivate" : "Activate"}
				</Button>
			</CardFooter>
		</Card>
	);
}

export default Service;
