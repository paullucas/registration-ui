(ns registration-ui.views
  (:require [re-frame.core :as re-frame]
            [ajax.core :refer [POST]]))


(def api-url "http://localhost:3000/")


(defn event-val [event]
  (-> event .-target .-value))


(defn validate-email []
  (let [email (re-frame/subscribe [:email])
        regex #"\S+@\S+\.\S+"]
    (if (= @email "")
      (re-frame/dispatch [:set-email-valid ""])
      (if (->> @email (re-matches regex) string?)
        (re-frame/dispatch [:set-email-valid ""])
        (re-frame/dispatch [:set-email-valid "has-error"])))))


(defn validate-phone []
  (let [phone (re-frame/subscribe [:phone])
        regex #"1?\W*([2-9][0-8][0-9])\W*([2-9][0-9]{2})\W*([0-9]{4})(\se?x?t?(\d*))?"]
    (if (= @phone "")
      (re-frame/dispatch [:set-phone-valid ""])
      (if (->> @phone (re-matches regex) first string?)
        (re-frame/dispatch [:set-phone-valid ""])
        (re-frame/dispatch [:set-phone-valid "has-error"])))))


(defn valid-data? [email phone email-valid phone-valid]
  (and (> (count email) 0)
       (> (count phone) 0)
       (= email-valid phone-valid "")))


(defn email-input []
  (let [email-valid (re-frame/subscribe [:email-valid])]
    [:div.form-group
     [:label.control-label {:for "inputEmail"} "Email Address"]
     [:div.col-md-12.col-sm-12 {:class @email-valid}
      [:input.form-control {:type "email"
                            :id "inputEmail"
                            :placeholder "Email"
                            :on-change (fn [event]
                                         (re-frame/dispatch [:set-email (event-val event)]))
                            :on-key-down (validate-email)}]]]))


(defn phone-input []
  (let [phone-valid (re-frame/subscribe [:phone-valid])]
    [:div.form-group
     [:label.control-label {:for "inputPhone"} "Phone Number"]
     [:div.col-md-12.col-sm-12 {:class @phone-valid}
      [:input.form-control {:type "tel"
                            :id "inputPhone"
                            :placeholder "Phone"
                            :on-change (fn [event]
                                         (re-frame/dispatch [:set-phone (event-val event)]))
                            :on-key-down (validate-phone)}]]]))


(defn input-field [name type id placeholder dispatch]
  [:div.form-group
   [:label.control-label {:for id} name]
   [:div.col-md-12.col-sm-12
    [:input.form-control {:type type
                          :id id
                          :placeholder name
                          :on-change dispatch}]]])


(defn first-name-input []
  (input-field "First Name" "text" "inputEmail" "First Name"
               (fn [event]
                 (re-frame/dispatch [:set-first-name (event-val event)]))))


(defn last-name-input []
  (input-field "Last Name" "text" "inputEmail" "Last Name"
               (fn [event]
                 (re-frame/dispatch [:set-last-name (event-val event)]))))


(defn username-input []
  (input-field "Username" "text" "inputEmail" "Username"
               (fn [event]
                 (re-frame/dispatch [:set-username (event-val event)]))))


(defn submit-btn-post-req [email phone first-name last-name username]
  (when (not (and (= email "") (= phone "")))
    (let [timestamp (.toString (new js/Date))
          uuid (subs js/window.location.search 6)]
      (POST api-url
            {:headers {"Content-Type" "application/json; charset=utf-8"}
             :body (->> {:email email
                         :phone phone
                         :firstname first-name
                         :lastname last-name
                         :username username
                         :timestamp timestamp
                         :uuid uuid}
                        clj->js
                        (.stringify js/JSON))
             :handler (fn [response] (js/console.log response))
             :format :json
             :response-format :text
             :keywords? true}))))


(defn submit-btn []
  (let [email (re-frame/subscribe [:email])
        phone (re-frame/subscribe [:phone])
        first-name (re-frame/subscribe [:first-name])
        last-name (re-frame/subscribe [:last-name])
        username (re-frame/subscribe [:username])
        email-valid (re-frame/subscribe [:email-valid])
        phone-valid (re-frame/subscribe [:phone-valid])]
    [:div.col-md-12.col-sm-12.submitBtnContainer
     [:div.btn.btn-default.submitBtn
      {:class (if (valid-data? @email @phone @email-valid @phone-valid)
                ""
                "disabled")
       :on-click (fn []
                   (if (valid-data? @email @phone @email-valid @phone-valid)
                     (submit-btn-post-req @email @phone @first-name @last-name @username)))}
      "Submit"]]))


(defn info-form []
  [:div.col-md-6.col-md-offset-3.col-sm-12.infoFormContainer
   [:form.form-horizontal
    [first-name-input]
    [last-name-input]
    [username-input]
    [email-input]
    [phone-input]
    [submit-btn]]])


(defn header []
  [:div.col-md-12.col-sm-12.headerContainer
   [:h1.headerTitle "Registration"]])


(defn main-panel []
  (fn []
    [:div.container-fluid
     [header]
     [info-form]]))
