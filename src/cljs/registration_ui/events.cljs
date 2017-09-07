(ns registration-ui.events
  (:require [re-frame.core :as re-frame]
            [registration-ui.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-email
 (fn [db [_ email]]
   (assoc db :email email)))

(re-frame/reg-event-db
 :set-phone
 (fn [db [_ phone]]
   (assoc db :phone phone)))

(re-frame/reg-event-db
 :set-email-valid
 (fn [db [_ email-valid]]
   (assoc db :email-valid email-valid)))

(re-frame/reg-event-db
 :set-phone-valid
 (fn [db [_ phone-valid]]
   (assoc db :phone-valid phone-valid)))

(re-frame/reg-event-db
 :set-first-name
 (fn [db [_ first-name]]
   (assoc db :first-name first-name)))

(re-frame/reg-event-db
 :set-last-name
 (fn [db [_ last-name]]
   (assoc db :last-name last-name)))

(re-frame/reg-event-db
 :set-username
 (fn [db [_ username]]
   (assoc db :username username)))
