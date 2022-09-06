
(ns app.site-config.frontend.views
  (:require
            [x.app-core.api       :as a :refer [r]]
            [x.app-elements.api   :as elements]
            [x.app-components.api :as components]

            [plugins.view-selector.api :as view-selector]
            [app.storage.frontend.api :as storage]

            [forms.api         :as forms]
            [mid-fruits.candy  :refer [param]]
            [mid-fruits.string :as string]

            [layouts.surface-a.api     :as surface-a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(def DEFAULT-VIEW :contact-info)

(defn bubble-save-body []
  [elements/text {:content :saved}])

(defn facebook []
  [elements/text-field ;::my-text-field-w-surface
   {:label "Facebook"
    :value-path [:site-config :editor-data :site-config/facebook]
    :placeholder "https://www.facebook.com/..."}])

(defn twitter []
  [elements/text-field ;::my-text-field-w-surface
   {:label "Twitter"
    :value-path [:site-config :editor-data :site-config/instagram]
    :placeholder "https://www.twitter.com/..."}])

(defn instagram []
  [elements/text-field ;::my-text-field-w-surface
   {:label "Instagram"
    :value-path [:site-config :editor-data :site-config/twitter]
    :placeholder "https://www.instagram.com/..."}])

(defn youtube []
  [elements/text-field ;::my-text-field-w-surface
   {:label "Youtube"
    :value-path [:site-config :editor-data :site-config/youtube]
    :placeholder "https://www.youtube.com/..."}])

(defn email-address []
  [elements/text-field
   {:label :email-address
     :placeholder "sample@email.com"
     :value-path [:site-config :editor-data :site-config/email-address]}])

(defn phone-number []
  [elements/text-field
   {:label :phone-number
    :placeholder "+36 30 457 7157"
    :modifier #(string/starts-with! % "+")
    :value-path [:site-config :editor-data :site-config/phone-number]}])

(defn viber []
  [elements/text-field
   {:label "Viber"
     :value-path [:site-config :editor-data :site-config/viber]
     :placeholder "+36 00 123 4567"
     :modifier #(string/starts-with! % "+")}])

(defn address []
  [elements/text-field
   {:label :address
     :placeholder "3141 Kiskőrös, Kör körút 360"
     :value-path [:site-config :editor-data :site-config/address]}])

(defn google-maps []
  [elements/text-field
   {:label :google-maps-link
     :placeholder "https://goo.gl/maps/..."
     :value-path [:site-config :editor-data :site-config/google-maps-link]}])

(defn social-media []
  [:<>
   [:div (forms/form-row-attributes)
    [:div (forms/form-block-attributes {:ratio 30})
     [facebook]]
    [:div (forms/form-block-attributes {:ratio 30})
     [twitter]]
    [:div (forms/form-block-attributes {:ratio 30})
     [instagram]]
    [:div (forms/form-block-attributes {:ratio 30})
     [youtube]]]])


(defn contact-info []
  [:div (forms/form-row-attributes)
   [:div (forms/form-block-attributes {:ratio 30})
     [email-address]
     [phone-number]
     [viber]]
   [:div (forms/form-block-attributes {:ratio 60})
    [address]
    [google-maps]]])

(defn notification-email []
  [elements/text-field
   {:label :email-address-to-receive
    :value-path [:site-config :editor-data :site-config/notification-email]}])


(defn home-page-meta-keywords []
  [elements/text-field
   {:label :meta-keywords
    :value-path [:site-config :editor-data :site-config/home-page-meta-keywords]}])

(defn home-page-meta-description []
  [elements/multiline-field
   {:label :meta-description
    :value-path [:site-config :editor-data :site-config/home-page-meta-description]}])

(defn home-page-description []
  [elements/multiline-field
   {:label :home-page-description
    :value-path [:site-config :editor-data :site-config/home-page-description]}])

(defn home-page-image []
  [storage/media-picker {:label :home-page-image
                         :value-path [:site-config :editor-data :site-config/home-page-image]}])

(defn home-page []
  [:div
   [:div (forms/form-row-attributes)
    [:div (forms/form-block-attributes {:ratio 100})
      [home-page-description]
      [home-page-meta-keywords]
      [home-page-meta-description]]
    [:div (forms/form-block-attributes {:ratio 50})
     [home-page-image]]
    [:div (forms/form-block-attributes {:ratio 50})
     [notification-email]]]])







(defn site-config-body []
  (let [view-key (a/subscribe [:view-selector/get-selected-view-id :site-config])]
    (fn []
      [:div {:style {:width "100%" :padding-top :40px}}
       (case (or @view-key DEFAULT-VIEW)
         :contact-info [contact-info]
         :social-media [social-media]
         :home-page    [home-page])])))

(defn menu-item [label view-key]
  (let [selected-view-key (a/subscribe [:view-selector/get-selected-view-id :site-config])]
    {:label label
     :active? (= view-key @selected-view-key)
     :on-click [:view-selector/go-to! :site-config view-key]}))

(defn site-config-header
  [surface-id {:keys [form-completed?] :as header-props}]
  (let [view-id  (a/subscribe [:view-selector/get-selected-view-id :site-config])]
    (fn [surface-id {:keys [form-completed?] :as header-props}]
      [elements/horizontal-polarity
       {:start-content
        [elements/menu-bar
         {:menu-items [(menu-item :contact-info :contact-info)
                       (menu-item :social-media :social-media)
                       (menu-item :home-page    :home-page)]}]
        :end-content [:<>
                      [elements/button ::save-client-button
                       {:tooltip :save! :preset :save-icon-button
                        :on-click [:site-config/request-save!]}]]}])))

(defn view
  [surface-id]
  (let [description (a/subscribe [:site-config/description])]
    (fn [surface-id])))
      ;[layouts/layout-a ::view
      ;                  {:body   {:content    #'site-config-body}
      ;                   :header {:content    #'site-config-header
      ;                            :sticky?    true
                                          ;:subscriber [::get-header-view-props]}
      ;                   :label   :website-configuration
      ;                   :description @description])))


;                        {:default-view-id  :contact-info
;                         :allowed-view-ids [:contact-info :social-media :home-page]}])))
