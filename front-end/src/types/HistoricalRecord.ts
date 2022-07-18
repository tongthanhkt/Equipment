export default interface HistoricalRecord {
    id?: string;
    equipment_id?: string;
    user?: string|null;
    action_time?: string;
    status?: string;
    verifier?: string|null;
    performer?: string;
    type_action?: number;
    message?: string|null;
    cost?: string|null;
    created_by?: string;
    created_time?: string;
    updated_by?: string;
    updated_time?: string;
    metadata_info?: Object;
    device_id?: string,
    equipment_name?: string,
    take_over_status? : string|null,
    reason? : string,
  }