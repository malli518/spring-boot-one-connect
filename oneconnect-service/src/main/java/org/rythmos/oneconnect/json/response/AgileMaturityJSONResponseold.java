package org.rythmos.oneconnect.json.response;

import org.springframework.stereotype.Component;

@Component
public class AgileMaturityJSONResponseold {

	private InnovationJSONResponse innovationJSONResponse;

	private PartnershipJSONResponse partnershipJSONResponse;

	private EnthusiasmJSONResponse enthusiasmJSONResponse;

	private TeamworkJSONResponse teamworkJSONResponse;

	private FocusJSONResponse focusJSONResponse;

	private CandorJSONResponse candorJSONResponse;

	public InnovationJSONResponse getInnovation() {
		return innovationJSONResponse;
	}

	public void setInnovation(InnovationJSONResponse innovationJSONResponse) {
		this.innovationJSONResponse = innovationJSONResponse;
	}

	public PartnershipJSONResponse getPartnership() {
		return partnershipJSONResponse;
	}

	public void setPartnership(PartnershipJSONResponse partnershipJSONResponse) {
		this.partnershipJSONResponse = partnershipJSONResponse;
	}

	public EnthusiasmJSONResponse getEnthusiasm() {
		return enthusiasmJSONResponse;
	}

	public void setEnthusiasm(EnthusiasmJSONResponse enthusiasmJSONResponse) {
		this.enthusiasmJSONResponse = enthusiasmJSONResponse;
	}

	public TeamworkJSONResponse getTeamwork() {
		return teamworkJSONResponse;
	}

	public void setTeamwork(TeamworkJSONResponse teamworkJSONResponse) {
		this.teamworkJSONResponse = teamworkJSONResponse;
	}

	public FocusJSONResponse getFocus() {
		return focusJSONResponse;
	}

	public void setFocus(FocusJSONResponse focusJSONResponse) {
		this.focusJSONResponse = focusJSONResponse;
	}

	public CandorJSONResponse getCandor() {
		return candorJSONResponse;
	}

	public void setCandor(CandorJSONResponse candorJSONResponse) {
		this.candorJSONResponse = candorJSONResponse;
	}

	@Override
	public String toString() {
		return "AgileMaturityJSONResponseold [innovationJSONResponse = " + innovationJSONResponse
				+ ", partnershipJSONResponse = " + partnershipJSONResponse + ", enthusiasmJSONResponse = "
				+ enthusiasmJSONResponse + ", teamworkJSONResponse = " + teamworkJSONResponse + ", focusJSONResponse = "
				+ focusJSONResponse + ", candorJSONResponse = " + candorJSONResponse + "]";
	}

}
