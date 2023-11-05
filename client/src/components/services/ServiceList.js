import React from "react";
import { SimpleGrid } from "@chakra-ui/react";
import Service from "./Service";

function ServiceList({ myService = false, services, openPopup }) {
	return (
		<SimpleGrid
			spacing={4}
			templateColumns="repeat(auto-fill, minmax(200px, 1fr))"
			borderRadius={8}
			m={3}
			p={2}
		>
			{services.map((service, index) => (
				<Service
					arrIndex={index}
					key={service.id}
					myService={myService}
					serviceDetails={service}
					openPopup={openPopup}
				/>
			))}
		</SimpleGrid>
	);
}

export default ServiceList;
