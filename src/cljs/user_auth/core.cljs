(ns user-auth.core
  (:require
   [day8.re-frame.http-fx]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [goog.events :as events]
   [goog.history.EventType :as HistoryEventType]
   [markdown.core :refer [md->html]]
   [user-auth.ajax :as ajax]
   [user-auth.events]
   [reitit.core :as reitit]
   [clojure.string :as string]
   [user-auth.db.general-con :as db])
  (:import goog.History))

(defn nav-link [uri title page]
  [:a.navbar-item
   {:href   uri
    :class (when (= page @(rf/subscribe [:page])) :is-active)}
   title])

(defn navbar []
  (r/with-let [expanded? (r/atom false)]
    [:nav.navbar.is-info>div.container
     [:div.navbar-brand
      [:a.navbar-item {:href "/" :style {:font-weight :bold}} "user-auth"]
      [:span.navbar-burger.burger
       {:data-target :nav-menu
        :on-click #(swap! expanded? not)
        :class (when @expanded? :is-active)}
       [:span][:span][:span]]]
     [:div#nav-menu.navbar-menu
      {:class (when @expanded? :is-active)}
      [:div.navbar-start
       [nav-link "#/" "Home" :home]
       [nav-link "#/about" "About" :about]
       [nav-link "#/sign-in" "Sign In" :sign-in]]]]))

(defn about-page []
  [:section.section>div.container>div.content
   [:img {:src "/img/warning_clojure.png"}]])

(defn home-page []
  [:div "Check out our sign in page ^^"])
  ; [:section.section>div.container>div.content
  ;  (when-let [docs @(rf/subscribe [:docs])]
  ;    [:div {:dangerouslySetInnerHTML {:__html (md->html docs)}}
  ;     ])])










(defn generic-prompt 
  "A message that will pop-up to help user know what input is expected"
  [message]
  [:div.mymessages
   [:div.prompt-messages
    [:em message]]]) ;;TODO - change font size to be smaller? or something to make this stand out less




(defn input-element 
  "An input element that takes in what type of element it is, and the current value that will change with input."
  [id name type value in-focus]
  [:input {:id id
           :name name
           :class "form-control"
           :type type
           :required ""
           :value @value
           :on-change #(reset! value (-> % .-target .-value))
           ;; Below we change the whether the input is focused on or not
           :on-focus #(swap! in-focus not)
           :on-blur #(swap! in-focus not)
           }])


(defn email-input [email-address-atom]
  [input-element "email" "email" "email" email-address-atom])



(defn input-and-prompt
  "Input-element mixed with a generic-prompt"
  [label-value input-name input-type input-element-arg prompt-element]
  (let [input-focus (r/atom false)]
    (fn []
      [:div
       [:label label-value]
       (if @input-focus prompt-element [:div])
       [input-element input-name input-name input-type input-element-arg input-focus]])))

(defn email-form [email-address-atom]
  (input-and-prompt "Email:"
                    "email"
                    "email"
                    email-address-atom
                    [generic-prompt "What's your email?"]))

(defn password-form [password-atom]
  (input-and-prompt "Password:"
                    "password"
                    "password"
                    password-atom
                    [generic-prompt "Enter your password"]))






(defn sign-up-button 
  "Signs up a new user using a hugsql call"
  [username pass]
  db/create-new-user username pass)

(defn login-button []
  )





(defn sign-in-page []
  (let [email-address (r/atom nil)
        password (r/atom nil)]
    (fn []
      [:div.container
       [:div.content-body
        [:h1 "Please Sign In" ;; this isn't big for some reason
         [:div.box
          [:form
         ;;[email-input email-address]
           [email-form email-address]
           [password-form password]]
          [:p "email = " @email-address]
          [:p "password = " @password]
          [:button "Login"]
          [:button "Sign Up?"]]
         ]]]))
  )

(def pages
  {:home #'home-page
   :about #'about-page
   :sign-in #'sign-in-page})

(defn page []
  [:div
   [navbar]
   [(pages @(rf/subscribe [:page]))]])

;; -------------------------
;; Routes

(def router
  (reitit/router
    [["/" :home]
     ["/about" :about]
     ["/sign-in" :sign-in]]))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      HistoryEventType/NAVIGATE
      (fn [event]
        (let [uri (or (not-empty (string/replace (.-token event) #"^.*#" "")) "/")]
          (rf/dispatch
            [:navigate (reitit/match-by-path router uri)]))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn mount-components []
  (rf/clear-subscription-cache!)
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:navigate (reitit/match-by-name router :home)])
  
  (ajax/load-interceptors!)
  (rf/dispatch [:fetch-docs])
  (hook-browser-navigation!)
  (mount-components))
