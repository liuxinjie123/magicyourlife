package com.miracle;

import java.util.*;

public final class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		List<Country> countryList = new ArrayList<>();
		for (int i=0; i<n; i++) {
			Country country = new Country();
			country.cityCount = scanner.nextInt();
			country.airLineCount = scanner.nextInt();
			if (0 != country.airLineCount) {
				List<AirLine> airLineList = new ArrayList<>();
				for (int j=0; j<country.airLineCount; j++) {
					int from = scanner.nextInt();
					int to = scanner.nextInt();
					airLineList.add(new AirLine(from, to));
				}
				country.airLineList = airLineList;
			}
			countryList.add(country);
		}

		// check
		checkAirLine(countryList);

		return;
	}

	private static void checkAirLine(List<Country> countryList) {
		loop:
		for (int i=1; i<=countryList.size(); i++) {
			Country country = countryList.get(i-1);
			if (country.airLineCount == 0) {
				System.out.println("Case " + i + ": YES");
				continue loop;
			} else {
				for (AirLine airLine : country.airLineList) {
					if (checkIfExists(airLine, country.airLineList)) {
						System.out.println("Case " + i + ": YES");
						continue loop;
					}
				}
				System.out.println("Case " + i + ": NO");
			}
		}
	}

	static boolean checkIfExists(AirLine air, List<AirLine> airLineList) {
		for (AirLine airLine : airLineList) {
			if (airLine.from == air.from) {
				continue;
			} else {
				if (airLine.from == air.to) {
					return false;
				}
				if (airLine.to == air.to) {
					return false;
				}
				if (airLine.to == air.from) {
					return false;
				}
				air = airLine;
			}
		}
		return true;
	}

	static class Country {
		int cityCount;
		int airLineCount;
		List<AirLine> airLineList;
	}

	static class AirLine {
		int from;
		int to;
		public AirLine() {}
		public AirLine(int from, int to) {
			this.from = from;
			this.to = to;
		}
	}
}
