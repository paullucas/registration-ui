(ns registration-ui.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :email
 (fn [db]
   (:email db)))

(re-frame/reg-sub
 :phone
 (fn [db]
   (:phone db)))

(re-frame/reg-sub
 :email-valid
 (fn [db]
   (:email-valid db)))

(re-frame/reg-sub
 :phone-valid
 (fn [db]
   (:phone-valid db)))

(re-frame/reg-sub
 :first-name
 (fn [db]
   (:first-name db)))

(re-frame/reg-sub
 :last-name
 (fn [db]
   (:last-name db)))

(re-frame/reg-sub
 :username
 (fn [db]
   (:username db)))
