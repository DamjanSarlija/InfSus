
import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import HotelsPage from "../HotelsPage";
import { MemoryRouter } from "react-router-dom";

const axios = require("axios");
jest.mock("axios");

describe("HotelsPage integration test", () => {
    it("prikazuje mockirane hotele", async () => {
        axios.get.mockImplementation((url) => {
            if (url.includes("/hotels/getAll/minimal")) {
                return Promise.resolve({
                    data: [
                        { id: 1, name: "Mock Hotel 1", address: "Address 1" },
                        { id: 2, name: "Mock Hotel 2", address: "Address 2" }
                    ]
                });
            }
            if (url.includes("/users/getAll")) {
                return Promise.resolve({ data: [] });
            }
            return Promise.resolve({ data: [] });
        });

        render(
            <MemoryRouter>
                <HotelsPage />
            </MemoryRouter>
        );

        expect(await screen.findByText("Mock Hotel 1")).toBeInTheDocument();
        expect(screen.getByText("Mock Hotel 2")).toBeInTheDocument();
    });
});
